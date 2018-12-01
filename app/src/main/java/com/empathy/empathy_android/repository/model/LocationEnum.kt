package com.empathy.empathy_android.repository.model

enum class LocationEnum(val code: Int, val location: String) {
    Seoul(1, "Seoul"),
    Incheon(1,"Incheon"),
    Daejeon(2,"Daejeon"),
    Daegu(3,"Daegue"),
    Gwangju(4,"Gwangju"),
    Busan(5,"Busan"),
    Ulsan(6,"Ulsan"),
    Sejong(7,"Sejong"),
    GyeonggiDo(8,"GyeonggiDo"),
    GangwonDo(9,"GangwonDo"),
    ChungcheongbukDo(10,"ChungcheongbukDo"),
    ChungcheongnamDo(11,"ChungcheongnamDo"),
    GyeongsangbukDo(12,"GyeongsangbukDo"),
    GyeongsangnamDo(13,"GyeongsangnamDo"),
    Jeollabukdo(14,"Jeollabukdo"),
    JeollanamDo(15,"JeollanamDo"),
    Jejudo(16,"Jejudo"),
    None(0, "")
}