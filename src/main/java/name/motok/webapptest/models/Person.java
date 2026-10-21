//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package name.motok.webapptest.models;

import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class Person {
    private int id;
    private @Size(
            min = 2,
            max = 30,
            message = "between 2 and 30 characters"
    ) String name;
    private @Min(
            value = 0L,
            message = "0+"
    ) int age;
    private @Pattern(
            regexp = "[A-Z]\\w+, [A-Z]\\w+, \\d{6}",
            message = "Russia, Moscow, 123456"
    ) String address;

    public Person(int id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.address = this.address;
    }

    public Person() {
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public int getAge() {
        return this.age;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
