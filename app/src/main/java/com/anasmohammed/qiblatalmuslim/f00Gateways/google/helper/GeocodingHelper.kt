package com.anasmohammed.qiblatalmuslim.f00Gateways.google.helper

import com.anasmohammed.qiblatalmuslim.f00Gateways.google.domain.model.AddressData
import com.anasmohammed.qiblatalmuslim.f00Gateways.google.domain.model.AddressFromLocationResponse

fun getAddressData(response: AddressFromLocationResponse?): AddressData {
    var route = ""
    var administrativeAreaLevel1 = ""
    var administrativeAreaLevel2 = ""
    var administrativeAreaLevel3 = ""
    var country = ""

    response?.results?.get(0)?.addressComponents?.let { components ->
        components.forEach { component ->
            component.types?.forEach { type ->
                when (type) {
                    "country" -> country = component.longName ?: ""
                    "administrative_area_level_3" -> administrativeAreaLevel3 = component.longName ?: ""
                    "administrative_area_level_2" -> administrativeAreaLevel2 = component.longName ?: ""
                    "administrative_area_level_1" -> administrativeAreaLevel1 = component.longName ?: ""
                    "route" -> route = component.longName ?: ""
                }
            }
        }
    }

    return AddressData(
        route = route,
        administrativeAreaLevel1 = administrativeAreaLevel1,
        administrativeAreaLevel2 = administrativeAreaLevel2,
        administrativeAreaLevel3 = administrativeAreaLevel3,
        country = country
    )
}
