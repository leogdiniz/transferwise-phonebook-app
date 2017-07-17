package hu.transferwise.phonebookapi.util.hashedid;

public class HashedId {

    private final Long id;
    private final String hash;

    public HashedId(Long id, String hash) {
        this.id = id;
        this.hash = hash;
    }

    public Long getId() {
        return id;
    }

    public String getHash() {
        return hash;
    }

}