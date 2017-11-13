# ritchielq
###### \java\seedu\address\testutil\PersonBuilder.java
``` java
    /**
     * Sets the {@code NusModules} of the {@code Person} that we are building.
     */
    public PersonBuilder withNusModules(String nusModule) {
        NusModules newNusModules = new NusModules();

        String[] lessons = nusModule.split("&");
        for (String lesson : lessons) {
            int index1 = lesson.indexOf("[");
            int index2 = lesson.indexOf("]");
            int index3 = lesson.indexOf("=");
            String moduleCode = lesson.substring(0, index1);
            String lessonType = lesson.substring(index1 + 1, index2);
            String lessonSlot = lesson.substring(index3 + 1);

            HashMap<String, String> lessonHm = new HashMap<>();
            lessonHm.put(lessonType, lessonSlot);
            try {
                newNusModules = newNusModules.addModule(moduleCode, lessonHm);
            } catch (IllegalValueException e) {
                throw new IllegalArgumentException("nusModule is wrong format.");
            }
        }
        this.person.setNusModules(newNusModules);

        return this;
    }

```
