package ca.macewan.c305;

import java.util.*;

public class Businesses {
    private List<Business> businessList;
    private boolean sorted;

    public Businesses(){
        this.businessList = new ArrayList<>();
        this.sorted = false;
    }

    public int length(){
        return this.businessList.size();
    }

    public Business getBusiness(int index) {
        return this.businessList.get(index);
    }

    public List<Business> getBusinessList() {
        return this.businessList;
    }

    public static String setStr(String val){ //helper method for setProp
        if ((val.length() > 0) && (val != " ")) {
            return val;
        }
        return "";
    }

    public static int setInt(String num){
        if (num.length() > 0) {
            return Integer.parseInt(num);
        }
        return 0;
    }

    public Business setBusiness(String license, String category, String tradeName, String address, String nId,
                                String nbhd, String ward, String lat, String lng) {
        int lic = convertLicenseToInt(license);
        Address add = convertToAddress(address);
        Location loc = new Location(Double.parseDouble(lat), Double.parseDouble(lng));
//        if (!loc.latRangeCheck()) {
//            throw new IllegalArgumentException("Illegal range for latitude");
//        }
//        if (!loc.longRangeCheck()) {
//            throw new IllegalArgumentException("Illegal range for latitude");
//        }
        return new Business(lic, category, tradeName, add, new Neighbourhood(setInt(nId), setStr(nbhd),
                setStr(ward)), new Location(Double.parseDouble(lat), Double.parseDouble(lng)));
    }

    public Business makeNewBusiness(int i) {
        int license = this.getBusiness(i).getLicense();
        String category = this.getBusiness(i).getCategory();
        String tradeName = this.getBusiness(i).getTradeName();
        Address address = this.getBusiness(i).getAddress();
        Neighbourhood neighborhood = this.getBusiness(i).getNeighborhood();
        Location location = this.getBusiness(i).getLoc();
        return new Business(license, category, tradeName, address, neighborhood, location);
    }

    public void addBusiness(Business business) {
        this.businessList.add(business);
    }


    public void businessSort(){
        Collections.sort(this.businessList);
        this.sorted = true;
    }

    public Businesses findBusinessesByAddress(String address) {
        if(!this.sorted) {
            this.businessSort();
        }
        Businesses businessesByAddress = new Businesses();
        for(int i = 0; i < this.length(); i++) { //parse through list until acct is found
            if(this.getBusiness(i).getAddress().toString().toLowerCase().startsWith(address.toLowerCase())) {
                businessesByAddress.addBusiness(this.getBusiness(i));
            }
        }
        return businessesByAddress;
    }

    public Businesses findBusinessesByNeighborhood(String neighborhood) {
        if(!this.sorted) {
            this.businessSort();
        }
        Businesses businessesByNeighborhood = new Businesses();
        for(int i = 0; i < this.length(); i++) { //parse through list until acct is found
            if(this.getBusiness(i).getNeighborhood().toString().toLowerCase().startsWith(neighborhood.toLowerCase())) {
                businessesByNeighborhood.addBusiness(this.getBusiness(i));
            }
        }
        return businessesByNeighborhood;
    }

    public Businesses findBusinessesByCategory(String cls) {
        if(!this.sorted) {
            this.businessSort();
        }
        Businesses businessesByCategory = new Businesses();
        for(int i = 0; i < this.length(); i++) { //parse through list until acct is found
            if(this.getBusiness(i).getCategory().toLowerCase().startsWith(cls.toLowerCase())) {
                businessesByCategory.addBusiness(this.getBusiness(i));
            }
        }
        return businessesByCategory;
    }

    public ArrayList<String> getCategories(){
        Set<String> toPop = new HashSet<>();
        for(int i = 0; i < this.length(); i++){
            toPop.add(this.getBusiness(i).getCategory());
        }
        ArrayList<String> toRet = new ArrayList<>(toPop);
        return toRet;
    }

    public Address convertToAddress(String address) {
        String[] addressArray = address.split(",-");
        if(addressArray.length == 1) {
            return new Address(null, null, addressArray[0]);
        }
        if(addressArray.length == 2) {
            return new Address(null, addressArray[0], addressArray[1]);
        }
        if(addressArray.length == 3) {
            return new Address(addressArray[0], addressArray[1], addressArray[2]);
        }
        return null;
    }

    public int convertLicenseToInt(String license) {
        char[] licenseNum = license.toCharArray();
        char[] toConvertX = new char[licenseNum.length - 1];
        int j = 0;
        for (int i = 0; i < licenseNum.length; i++) {
            if(licenseNum[i] != '-') {
                toConvertX[j] = licenseNum[i];
                j++;
            }
        }
        String toConvertY = new String(toConvertX);
        int toRet = Integer.parseInt(toConvertY);
        return toRet;
    }

    public boolean equals(Object obj) {
        if (obj instanceof Businesses) {
            Businesses otherBusinesses= (Businesses) obj;
            if (this.length() == ((Businesses) obj).length()) {
                int count = 0;
                int i;
                for (i = 0; i < this.length(); i++) {
                    if (this.getBusiness(i).getLicense() == otherBusinesses.getBusiness(i).getLicense()) {
                        count++;
                    }
                }
                return i == count;
            }
        }
        return false;
    }

    public int hashCode() {
        if (this.length() != 0) {
            int result = Integer.hashCode(this.getBusiness(0).getLicense());
            for(int i = 1; i < this.length(); i++) {
                result = result * 31 + Integer.hashCode(this.getBusiness(i).getLicense());
            }
            return result;
        }
        return 0;
    }

    public String toString() {
        String toRet = "List of Business ID's: ";
        for(int i = 0; i < this.length(); i ++) {
            toRet += businessList.get(i).getLicense();
            if(this.length() - 1 != i){
                toRet += ", ";
            }
        }
        return toRet;
    }
}
