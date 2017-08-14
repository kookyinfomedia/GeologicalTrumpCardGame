package kookyinfomedia.com.gtcg;


class ModelClassIndia {
    private String state;
    private String districts;
    private String area;
    private String population;
    private String national_parks;
    private String river;
    private String crop;
    private String mineral;
    private byte map[];

    public byte[] getMap(){
        return map;
    }
    public void setMap(byte map[]){
        this.map=map;
    }
    String getState(){
        return state;
    }
    void setState(String state){
        this.state = state;
    }
    String getDistricts(){
        return districts;
    }
    void setDistricts(String districts){
        this.districts = districts;
    }
    String getArea(){
        return area;
    }
    void setArea(String area){
        this.area = area;
    }

    String getPopulation(){
        return population;
    }
    void setPopulation(String population){
        this.population = population;
    }
    String getNational_parks(){
        return national_parks;
    }
    void setNational_parks(String national_parks) {
        this.national_parks = national_parks;
    }

    String getRiver() {
        return river;
    }

    void setRiver(String river) {
        this.river = river;
    }

    String getCrop() {
        return crop;
    }

    void setCrop(String crop) {
        this.crop = crop;
    }

    String getMineral() {
        return mineral;
    }

    void setMineral(String mineral) {
        this.mineral = mineral;
    }

}