package test;

public enum RANKING {
	HIGHCARD(14),
    ONEPAIR(15),
    TWOPAIR(16),
    THREEOFAKIND(17),
    STRAIGHT(18),
    FLUSH(19),
    FULLHOUSE(20),
    FOUROFAKIND(21),
    STRAIGHTFLUSH(22),
    ROYALFLUSH(23);

    public final int value;

    private RANKING(int value) {
        this.value = value;
    }
}