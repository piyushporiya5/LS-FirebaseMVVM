package com.sociallogin.mvvm.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.io.Serializable

@Parcelize
data class UserDataModal(
    var uid: String,
    var name: String?,
    var email: String?
) : Parcelable {

}

