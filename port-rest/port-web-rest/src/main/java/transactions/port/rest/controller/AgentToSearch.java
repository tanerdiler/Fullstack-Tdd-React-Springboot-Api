package transactions.port.rest.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@NoArgsConstructor
public class AgentToSearch {

  private String firstname;
  private String lastname;
  private String email;

  public AgentToSearch(String firstname, String lastname, String email) {
      this.firstname = firstname;
      this.lastname = lastname;
      this.email = email;
  }

  @Override
  public boolean equals(Object o) {
      if (this == o)
          return true;
      if (o == null || getClass() != o.getClass())
          return false;
      AgentToSearch that = (AgentToSearch) o;
      return Objects.equals(firstname, that.firstname) && Objects.equals(lastname, that.lastname) && Objects.equals(email, that.email);
  }

  @Override
  public int hashCode() {
      return Objects.hash(firstname, lastname, email);
  }
}