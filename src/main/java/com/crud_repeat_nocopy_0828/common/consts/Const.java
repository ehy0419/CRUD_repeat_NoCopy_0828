package com.crud_repeat_nocopy_0828.common.consts;

public abstract class Const {
    private Const() {       // 인스턴스화 방지 ← 생성자를 private으로 막음 (new 금지)
        throw new AssertionError("Warning! No Const instances!");
    }

    /** == 상수/유틸 클래스는 인스턴스를 만들지 않도록 private 생성자(+ 보통 final)를 두어 “인스턴스화 방지”를 합니다. == **
     *
     * 인스턴스화 방지
     *
     * 인스턴스화는 new로 객체를 만드는 걸 말해요.
     * 인스턴스화 방지는 “이 클래스는 객체를 만들 목적이 아니다”를 코드로 금지하는 거예요.
     * 예를 들어 Const처럼 상수/유틸만 담는 클래스는 객체를 만들어봤자 쓸모가 없죠.
     *
     * private Const() 때문에 다른 클래스에서 new Const()가 컴파일 자체가 안 됨.
     * final은 “상속도 금지” → 유틸/상수 클래스는 확장 대상이 아님을 명확히.
     *
     * 이렇게까지 하는 이유는
     * 실수로 new Const() 해서 쓸모없는 객체 만드는 걸 차단
     * “이 클래스는 인스턴스 필요 없음(static 전용)”이라는 의도를 명확히
     * 메모리/가독성/설계 의도 모두 깔끔
     * */

    public static final String LOGIN_USER = "LOGIN_USER";
    public static final String LOGIN_USER_ID = "LOGIN_USER_ID";
}
