package threecirclesweb5

class Checkin {
    String description
    Place place
    static hasMany = [friends:Friend]
    byte[] photo
    static constraints = {
        photo maxSize: 20*1024*1024, nullable: true
    }
}
