package kookyinfomedia.com.gtcg;


public class ModelClassIndia {
    private String state;
    private String districts;
    private String area;
    private String population;
    private String national_parks;
    private String river;
    private String crop;
    private String mineral;
    private byte map[];
    private String festival;
    private String capital;
    public String getFestival() {
        return festival;
    }

    public void setFestival(String festival) {
        this.festival = festival;
    }

    public String getCapital() {
        return capital;
    }

    public void setCapital(String capital) {
        this.capital = capital;
    }


    public byte[] getMap()
    {
        return map;
    }
    public void setMap(byte map[])
    {
        this.map=map;
    }
    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getDistricts() {
        return districts;
    }

    public void setDistricts(String districts) {
        this.districts = districts;
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

    public String getNational_parks() {
        return national_parks;
    }

    public void setNational_parks(String national_parks) {
        this.national_parks = national_parks;
    }

    public String getRiver() {
        return river;
    }

    public void setRiver(String river) {
        this.river = river;
    }

    public String getCrop() {
        return crop;
    }

    public void setCrop(String crop) {
        this.crop = crop;
    }

    public String getMineral() {
        return mineral;
    }

    public void setMineral(String mineral) {
        this.mineral = mineral;
    }

}