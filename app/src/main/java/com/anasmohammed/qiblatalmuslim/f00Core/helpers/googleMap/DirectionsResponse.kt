package com.anasmohammed.qiblatalmuslim.f00Core.helpers.googleMap

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class DirectionsResponse(
    val geocoded_waypoints: List<GeocodedWaypoint>?,
    val routes: List<Route>?,
)

@Keep
data class Bounds(
    val northeast: Northeast?,
    val southwest: Southwest?
)

@Keep
data class DistanceMap(
    val text: String?,
    val value: Int?
)

@Keep
data class EndLocation(
    val lat: Double?,
    val lng: Double?
)

@Keep
data class GeocodedWaypoint(
    val geocoder_status: String?,
    val place_id: String?,
    val types: List<String?>?
)

@Keep
data class Polyline(
    val points: String?
)

@Keep
data class StartLocation(
    val lat: Double?,
    val lng: Double?
)

@Keep
data class Step(
    val distance: DistanceMap?,
    val duration: Duration?,
    val end_location: EndLocation?,
    val html_instructions: String?,
    val maneuver: String?,
    val polyline: Polyline?,
    val start_location: StartLocation?,
    val travel_mode: String?
)

@Keep
data class Southwest(
    val lat: Double?,
    val lng: Double?
)

@Keep
data class Route(
    val bounds: Bounds?,
    val copyrights: String?,
    val legs: List<Leg>?,
    val overview_polyline: OverviewPolyline?,
    val summary: String?,
    val warnings: List<Any>?,
    val waypoint_order: List<Any>?
)

@Keep
data class OverviewPolyline(
    val points: String?
)

@Keep
data class Northeast(
    val lat: Double?,
    val lng: Double?
)

@Keep
data class Duration(
    val text: String?,
    val value: Int?
)

@Keep
data class Leg(
    val distance: Distance?,
    val duration: Duration?,
    val end_address: String?,
    val end_location: EndLocation?,
    val start_address: String?,
    val start_location: StartLocation?,
    val steps: List<Step>?,
    val traffic_speed_entry: List<Any>?,
    val via_waypoint: List<Any>?
)

data class Distance(
    @SerializedName("value")
    val value: Double? = 0.0,
    @SerializedName("unit")
    val unit: String? = "",
)