package ru.skillbranch.devintensive.extensions

import ru.skillbranch.devintensive.models.User
import ru.skillbranch.devintensive.models.UserView
import ru.skillbranch.devintensive.utils.Utils

fun User.toUserView(): UserView {

    val nickName = Utils.transliteration("$firstName $lastName")
    val initials = Utils.toInitials(firstName, lastName)
    val humanizedLastVisit = lastVisit?.humanizeDiff()
    val status = if (lastVisit == null) "Еще ни разу не был" else if (isOnline) "online" else "Последний раз был $humanizedLastVisit"

    return UserView(
        id,
        fullName = "$firstName $lastName",
        nickName =  nickName,
        initials =  initials,
        status = status,
        avatar = avatar
    )
}