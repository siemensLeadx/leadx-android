package com.siemens.leadx.data.local.entities

import com.siemens.leadx.data.remote.entites.LookUp

class CreateLookUps(
    var businessOpportunities: List<LookUp>?,
    var customerStatus: List<LookUp>?,
    var devices: List<LookUp>?,
    var regions: List<LookUp>?,
    var sectors: List<LookUp>?,
)
