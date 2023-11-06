package com.appxstudios.drawerapp.model

import java.io.Serializable

data class Note(
    var title: String,
    var content: String,
    var date:String,
): Serializable;
