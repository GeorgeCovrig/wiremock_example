package mockito.example.pojos;

import java.util.Objects;

public class Pilot {
     private int id;
     private String fullName;
     private String emailAddress;

     public Pilot(int id, String fullName, String emailAddress) {
          this.id = id;
          this.fullName = fullName;
          this.emailAddress = emailAddress;
     }

     public int getId() {
          return id;
     }

     public void setId(int id) {
          this.id = id;
     }

     public String getFullName() {
          return fullName;
     }

     public void setFullName(String fullName) {
          this.fullName = fullName;
     }

     public String getEmailAddress() {
          return emailAddress;
     }

     public void setEmailAddress(String emailAddress) {
          this.emailAddress = emailAddress;
     }

     @Override
     public boolean equals(Object o) {
          if (this == o) return true;
          if (o == null || getClass() != o.getClass()) return false;
          Pilot pilot = (Pilot) o;
          return id == pilot.id && Objects.equals(fullName, pilot.fullName) && Objects.equals(emailAddress, pilot.emailAddress);
     }

     @Override
     public int hashCode() {
          return Objects.hash(id, fullName, emailAddress);
     }
}
