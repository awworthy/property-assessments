package ca.macewan.cmpt305;

public class Address {
    private String suite;
    private String hsNo;
    private String strNm;

    public Address(String suite, String hsNo, String strNm) {
        this.suite = suite;
        this.hsNo = hsNo;
        this.strNm = strNm;
    }
    public String getSuite() {
        return this.suite;
    }

    public String getHsNo() {
        return this.hsNo;
    }

    public String getStrNm() {
        return this.strNm;
    }

    // Override toString, equals, and hashCode here
    @Override
    public String toString() {
        if(suite != "") {
            return this.suite + " " + this.hsNo + " " + this.strNm;
        }
        else{
            return this.hsNo + " " + this.strNm;
        }
    }
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Address))
            return false;
        Address otherAddress = (Address) obj;
        return this.suite.equals(otherAddress.suite) && this.hsNo == otherAddress.hsNo && this.strNm.equals(otherAddress.strNm);
    }
    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + suite.hashCode();
        result = 31 * result + hsNo.hashCode();
        result = 31 * result +strNm.hashCode();
        return result;
    }


}
