package gil.server;

public class Person {
    private Integer id;
    private String name;
    private String email;

    public Person() {}

    public Person(Integer id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return this.email;
    }

    public Integer getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    @Override
    public boolean equals(Object obj)
    {
        boolean result = true;

        if(obj instanceof Person) {
            Person p = (Person) obj;

            if(!getName().equals(p.getName())){
                result = false;
            }

            if(!getEmail().equals(p.getEmail())) {
                result = false;
            }

            if(getId() != p.getId()) {
                result = false;
            }
        } else {
            result = false;
        }

        return result;
    }

    @Override
    public String toString() {
        String delimiter = ", ";

        return getId() + delimiter + getName() + delimiter + getEmail();
    }
}
