package dev.reprator.haat.di

import dev.reprator.haat.features.restaurant2pane.businessDetail.data.remote.modal.EntityStoreInfoContainer
import dev.reprator.haat.features.restaurant2pane.businessDetail.data.remote.modal.EntityStoreMenuContainer
import dev.reprator.haat.features.restaurant2pane.restaurants.data.remote.modal.EntityMenuContainer
import retrofit2.http.GET
import retrofit2.http.HeaderMap
import retrofit2.http.Path

interface ApiService {
    @GET("user/main-page/by-area/1?type=Market&userId=e1fb17e0-25e6-4809-ae71-ce3fbf6505ba")
    suspend fun menuData(@HeaderMap headerMap: Map<String, String> = HeaderMap): EntityMenuContainer

    @GET("venue/{storeId}/info?userLatitude=0.0&userLongitude=0.0&isByLocation=false")
    suspend fun venueInfo(@Path("storeId") storeId: String, @HeaderMap headerMap: Map<String, String> = HeaderMap): EntityStoreInfoContainer

    @GET("venue/{storeId}/menu")
    suspend fun venueMenu(@Path("storeId") storeId: String, @HeaderMap headerMap: Map<String, String> = HeaderMap): EntityStoreMenuContainer

    companion object {
        private val HeaderMap = mapOf(
            "areaName" to "Umm al-Fahem",
            "deviceName" to "arm64",
            "deviceToken" to "f7yUnGE5mEwgrFwvVCZ2Ht:APA91bH2toTLXCeHLCSAWutmQYEjOlqL4KXj-PBbAJalhk2iqkheYcBsicOfdREby1hDEJQZiO9a0QpNakC2j9ye4pq4qwHm57SPaW1MxuQALMS4Ex4Dh9A",
            "userId" to "e1fb17e0-25e6-4809-ae71-ce3fbf6505ba",
            "app-tracking-status" to "0",
            "appVersion" to "19.06",
            "Latitude" to "32.531765",
            "LocationAccuracy" to "5.0",
            "os" to "iOS",
            "areaId" to "1",
            "Platform" to "iOS",
            "locationWithIP" to "192.168.0.200_32.531765,35.149751",
            "timeStamp" to "1755438867.656672",
            "Longitude" to "35.149751",
            "Accept-Language" to "en-US",
            "Authorization" to "bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJlMWZiMTdlMC0yNWU2LTQ4MDktYWU3MS1jZTNmYmY2NTA1YmEiLCJqdGkiOiIzZjY2NWI1Zi0yNDMyLTQzOGItOGI5Yy03NjI4ZDA5ZGNjY2UiLCJleHAiOjE5MTMxOTkxMDgsImF1ZCI6Imh0dHBzOi8vdXNlci1hcHAtc3RhZ2luZy5pbnRlcm5hbC5oYWF0LmRlbGl2ZXJ5In0.HlktNCG7VjgnJxum5uIITVhHME8XhGya8nuOwzt3BEw",
            "LocationPermission" to "On",
            "anonymousUserId" to "B19C2AF7-478B-41CA-B986-91330961A760",
            "Platform-Version" to "19.06",
            "Accept" to "*/*",
            "Content-Type" to "application/json",
            "appLanguage" to "en-US",
        )
    }
}



