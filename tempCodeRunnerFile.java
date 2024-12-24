class CityNode {
    String cityName;
    int cityID;
    List<CityNode> subCities;

    public CityNode(String cityName, int cityID) {
        this.cityName = cityName;
        this.cityID = cityID;
        this.subCities = new ArrayList<>();
    }
}