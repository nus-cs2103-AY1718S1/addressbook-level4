# icehawker
###### \java\seedu\address\testutil\PersonBuilder.java
``` java
    /**
     * Sets the {@code Country} of the {@code Person} that we are building.
     */
    public PersonBuilder withCountry(String country) {
        this.person.setCountry(new Country(country));
        // any illegal values already caught in Phone, where code is extracted.
        return this;
    }

```
