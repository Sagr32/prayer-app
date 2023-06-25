package com.sakr.prayertimesapp.app.viewmodel


import android.os.CountDownTimer
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import com.sakr.prayertimesapp.domain.models.GenericApiResponse
import com.sakr.prayertimesapp.domain.models.PrayerTimesEntity
import com.sakr.prayertimesapp.domain.models.calendar.PrayerTimeCalendarResponse
import com.sakr.prayertimesapp.domain.models.qibla.QiblaResponse
import com.sakr.prayertimesapp.domain.usecases.*
import com.sakr.prayertimesapp.utils.findTimeDifference
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(
    private val getPrayerTimesCalendarUseCase: GetPrayerTimesFromRemoteUseCase,
    private val getPrayerTimesFromLocalUseCase: GetPrayerTimesFromLocalUseCase,
    private val savePrayerTimeCalendarResponse: SavePrayerTimeUseCase,
    private val userLatLngUseCase: UserLatLngUseCase,
    private val getAddressUseCase: GetAddressUseCase,
    private val getQiblaUseCase: GetQiblaDirectionUseCase,
    private val saveQiblaDirection: SaveQiblaDirection,
    private val setFirstTimeBool: SetFirstTimeBooleanUseCase
) : ViewModel() {

    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Main)

    private val _address = MutableStateFlow<String?>(null)
    val address: StateFlow<String?> = _address
    private val _viewPagerCounter: MutableStateFlow<Int> = MutableStateFlow(0)
    val viewPagerCounter: StateFlow<Int> = _viewPagerCounter

    private val _prayerTimes: MutableStateFlow<GenericApiResponse<PrayerTimeCalendarResponse>?> =
        MutableStateFlow(null)
    val prayerTimes: StateFlow<GenericApiResponse<PrayerTimeCalendarResponse>?> = _prayerTimes

    private val _prayerTimesFromLocal: MutableStateFlow<List<PrayerTimesEntity>?> =
        MutableStateFlow(null)
    val prayerTimesFromLocal: StateFlow<List<PrayerTimesEntity>?> = _prayerTimesFromLocal
    private val diffrenceInMin: MutableStateFlow<Long?> = MutableStateFlow(null)
    private val _nextPrayerName: MutableStateFlow<String?> = MutableStateFlow(null)
    val nextPrayerName: StateFlow<String?> = _nextPrayerName

    private val _nextPrayerTime: MutableStateFlow<String?> = MutableStateFlow(null)
    val nextPrayerTime: StateFlow<String?> = _nextPrayerTime

    private val _qiblaResponse: MutableStateFlow<GenericApiResponse<QiblaResponse>?> =
        MutableStateFlow(null)
    val qiblaResponse: StateFlow<GenericApiResponse<QiblaResponse>?> = _qiblaResponse


    fun getQiblaFromRemote(
        latitude: Double,
        longitude: Double,
    ) {
        viewModelScope.launch {
            _qiblaResponse.value = GenericApiResponse.Loading()
            try {
                _qiblaResponse.value = getQiblaUseCase.getFromRemote(latitude, longitude)
                saveQiblaDirection(
                    _qiblaResponse.value!!.data!!.data.direction,
                )

            } catch (e: Exception) {
                _qiblaResponse.value = GenericApiResponse.Error(e.message.toString())
            }
        }
    }


    fun getAddress() {
        val latLng = userLatLngUseCase.getUserLatLng()
        val latitude = latLng?.lat
        val longitude = latLng?.lng
        latitude?.let { _address.value = getAddressUseCase(it, longitude!!) }
    }

    private fun savePrayerTime(prayerTimeList: List<PrayerTimesEntity>) {
        viewModelScope.launch {
            savePrayerTimeCalendarResponse(prayerTimeList)
        }
    }


    fun getPrayerTimesCalendar(
    ) {
        Log.e("TAG", "getPrayerTimesCalendar: Called")
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val simpleDateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH)
        val latLng = userLatLngUseCase.getUserLatLng()
        Log.e("TAG", "getPrayerTimesCalendar: ${latLng?.lat}")
        Log.e("TAG", "getPrayerTimesCalendar: ${latLng?.lng}")
        viewModelScope.launch {

            _prayerTimes.value = GenericApiResponse.Loading()
            try {
                _prayerTimes.value = getPrayerTimesCalendarUseCase(
                    year = year,
                    month = month,
                    latitude = latLng?.lat!!,
                    longitude = latLng?.lng!!,
                    method = 2
                )

                savePrayerTime(
                    _prayerTimes.value!!.data!!.data.map {
                        PrayerTimesEntity(
                            id = it.date.gregorian.day.toInt(),
                            date = simpleDateFormat.parse(it.date.gregorian.date),
                            fajr = it.timings.fajr,
                            dhuhr = it.timings.dhuhr,
                            asr = it.timings.asr,
                            maghrib = it.timings.maghrib,
                            isha = it.timings.isha,
                            dayOfWeek = it.date.gregorian.weekday.en,
                            dayOfWeekAr = it.date.hijri.weekday.ar,
                            hijriDate = it.date.hijri.date,
                            latitude = latLng?.lat!!,
                            longitude = latLng?.lng!!,
                        )
                    }
                )

            } catch (e: Exception) {
                Log.e("TAG", "getPrayerTimesCalendar:catch  ${e.localizedMessage}")
                _prayerTimes.value = GenericApiResponse.Error(e.message.toString())
            }
        }
    }

    fun saveLocation(currentLatLng: LatLng) {
        userLatLngUseCase.saveUserLatLng(currentLatLng.latitude, currentLatLng.longitude)
    }

    fun getPrayerTimesFromLocal() {
        viewModelScope.launch {
            _prayerTimesFromLocal.value = getPrayerTimesFromLocalUseCase()
        }
    }

    fun setViewPagerCounter(counter: Int) {
        _viewPagerCounter.value = counter
    }

    fun calculateNextPrayerTimeAndName(prayerTimesEntity: PrayerTimesEntity) {
        val calendar = Calendar.getInstance()
        val currentHour = calendar.get(Calendar.HOUR_OF_DAY)
        val currentMinute = calendar.get(Calendar.MINUTE)
        val currentTime = "$currentHour:$currentMinute"
        if (currentTime < prayerTimesEntity.fajr) {
            _nextPrayerName.value = "Fajr"
            diffrenceInMin.value =
                prayerTimesEntity.fajr.findTimeDifference(currentTime)
        } else if (currentTime < prayerTimesEntity.dhuhr) {
            _nextPrayerName.value = "Dhuhr"
            diffrenceInMin.value =
                prayerTimesEntity.dhuhr.findTimeDifference(currentTime)
        } else if (currentTime < prayerTimesEntity.asr) {
            _nextPrayerName.value = "Asr"
            diffrenceInMin.value = prayerTimesEntity.asr.findTimeDifference(currentTime)
        } else if (currentTime < prayerTimesEntity.maghrib) {
            _nextPrayerName.value = "Maghrib"
            diffrenceInMin.value =
                prayerTimesEntity.maghrib.findTimeDifference(currentTime)
        } else if (currentTime < prayerTimesEntity.isha) {
            _nextPrayerName.value = "Isha"
            diffrenceInMin.value =
                prayerTimesEntity.isha.findTimeDifference(currentTime)
        }

    }

    fun startCountDownTimer() {
        Log.e("TAG", "startCountDownTimer: ${diffrenceInMin.value}")

        scope.launch {
            val timer = object : CountDownTimer(diffrenceInMin.value!!, 1000) {
                override fun onTick(millisUntilFinished: Long) {
                    val hms = String.format(
                        "%02d:%02d:%02d",
                        TimeUnit.MILLISECONDS.toHours(millisUntilFinished),
                        TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) - TimeUnit.HOURS.toMinutes(
                            TimeUnit.MILLISECONDS.toHours(
                                millisUntilFinished
                            )
                        ),
                        TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(
                            TimeUnit.MILLISECONDS.toMinutes(
                                millisUntilFinished
                            )
                        )
                    )
                    Log.e("TAG", "onTick: $hms")
                    _nextPrayerTime.value = (hms) //set text

                }

                override fun onFinish() {
                    _nextPrayerTime.value = null
                }
            }
            timer.start()
        }
    }

    override fun onCleared() {
        super.onCleared()
        scope.cancel()

    }

    fun setIsFirstTime(b: Boolean) {
        setFirstTimeBool(b)
    }


}