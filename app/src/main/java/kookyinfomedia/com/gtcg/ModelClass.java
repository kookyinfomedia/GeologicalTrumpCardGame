package kookyinfomedia.com.gtcg;


class ModelClass {
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

    String getCountry() {
        return country;
    }

    void setCountry(String country) {
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

    String getCoastline() {
        return coastline;
    }

    void setCoastline(String coastline) {
        this.coastline = coastline;
    }

    String getaUnits() {
        return aUnits;
    }

    void setaUnits(String aUnits) {
        this.aUnits = aUnits;
    }

    String getbCountries() {
        return bCountries;
    }

    void setbCountries(String bCountries) {
        this.bCountries = bCountries;
    }

    String gethPoint() {
        return hPoint;
    }

    void sethPoint(String hPoint) {
        this.hPoint = hPoint;
    }

    String gethPointName() {
        return hPointName;
    }

    void sethPointName(String hPointName) {
        this.hPointName = hPointName;
    }

    String getCapital() {
        return capital;
    }

    void setCapital(String capital) {
        this.capital = capital;
    }
}
