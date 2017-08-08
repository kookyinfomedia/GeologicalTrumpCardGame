package kookyinfomedia.com.gtcg;


public class ModelClass {
    private String country,area,population,coastline,aUnits,bCountries,hPoint,capital,hPointName;
    private byte map[],flag[];

    public byte[] getMap(){
        return map;
    }
    public void setMap(byte map[]){
        this.map=map;
    }

    public byte[] getFlag(){
        return flag;
    }
    public void setFlag(byte flag[]){
        this.flag=flag;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getPopulation() {
        return population;
    }

    public void setPopulation(String population) {
        this.population = population;
    }

    public String getCoastline() {
        return coastline;
    }

    public void setCoastline(String coastline) {
        this.coastline = coastline;
    }

    public String getaUnits() {
        return aUnits;
    }

    public void setaUnits(String aUnits) {
        this.aUnits = aUnits;
    }

    public String getbCountries() {
        return bCountries;
    }

    public void setbCountries(String bCountries) {
        this.bCountries = bCountries;
    }

    public String gethPoint() {
        return hPoint;
    }

    public void sethPoint(String hPoint) {
        this.hPoint = hPoint;
    }

    public String gethPointName() {
        return hPointName;
    }

    public void sethPointName(String hPoint) {
        this.hPointName = hPointName;
    }

    public String getCapital() {
        return capital;
    }

    public void setCapital(String hPoint) {
        this.capital = capital;
    }
}
