package com.john_halaka.core.data.mappers

import com.john_halaka.core.data.dto.AuthInfoSerializable
import com.john_halaka.core.data.dto.UserSerializable
import com.john_halaka.core.domain.auth.AuthInfo
import com.john_halaka.core.domain.auth.User

fun AuthInfoSerializable.toDomain() : AuthInfo {
    return AuthInfo(
        accessToken = accessToken,
        refreshToken = refreshToken,
        user = user.toDomain()
    )
}

fun AuthInfo.toSerializable() : AuthInfoSerializable {
    return AuthInfoSerializable(
        accessToken = accessToken,
        refreshToken = refreshToken,
        user = user.toSerializable()
    )
}



fun UserSerializable.toDomain() : User {
    return User(
        id = id,
        username = username,
        email = email,
        hasVerifiedEmail = hasVerifiedEmail,
        profilePictureUrl = profilePictureUrl
    )
}


fun User.toSerializable() : UserSerializable {
    return UserSerializable(
        id = id,
        username = username,
        email = email,
        hasVerifiedEmail = hasVerifiedEmail,
        profilePictureUrl = profilePictureUrl
    )
}
