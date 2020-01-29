[![Release](https://jitpack.io/v/no.iskra/hashedpassword.svg)](https://jitpack.io/#no.iskra/hashedpassword)

# HashedPassword

A class that hashes a password with a unique salt and stores the hash. It can then validate a password against the hash.

To keep the class simple, it does not allow password changes. Instead, just create a new instance.

Javadoc hosted by Jitpack can be found [here](https://javadoc.jitpack.io/no/iskra/hashedpassword/master-SNAPSHOT/javadoc)

## Importing

The library is hosted on [Jitpack](https://jitpack.io/#no.iskra/hashedpassword). Gradle example:

    allprojects {
      repositories {
        jcenter()
        maven { url "https://jitpack.io" }
      }
    }
    dependencies {
      compile 'no.iskra:hashedpassword:0.4+'
    }

## Usage

    HashedPassword password = new HashedPassword("abc");
    
    password.validate("abc"); // Returns true
    
    password.validate("bcd"); // Returns false
