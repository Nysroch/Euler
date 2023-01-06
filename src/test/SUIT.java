package test;

public enum SUIT {
	
	DIAMONDS('D'),
	HEARTS('H'),
	SPADES('S'),
	CLUBS('C');
	

    public final char label;

    private SUIT(char label) {
        this.label = label;
}
    }
