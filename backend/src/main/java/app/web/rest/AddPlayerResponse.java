package app.web.rest;

class AddPlayerResponse {
    private String id;

    private AddPlayerResponse() {
    }

    public AddPlayerResponse(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
