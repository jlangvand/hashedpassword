// Copyright (c) 2020 Joakim Skogø Langvand
//
// GNU GENERAL PUBLIC LICENSE
//    Version 3, 29 June 2007
//
// This program is free software: you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.
//
// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.
//
// You should have received a copy of the GNU General Public License
// along with this program.  If not, see <http://www.gnu.org/licenses/>.

package no.iskra.hashedpassword;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

/**
 * Hashes a provided password with a unique salt. Provides a method to validate a password against
 * the stored hash.
 */
public class HashedPassword {
  private byte[] salt;
  private byte[] hash;

  /** @param pwd Password to be hashed. */
  public HashedPassword(String pwd) {
    SecureRandom random = new SecureRandom();
    this.salt = new byte[16];
    random.nextBytes(this.salt);
    this.hash = genHash(pwd, this.salt);
  }

  /**
   * Validate provided String against stored hash.
   *
   * @param pwd Password to validate.
   * @return True if password validates.
   */
  public boolean validate(String pwd) {
    byte[] pwdHash = genHash(pwd, this.salt);
    for (int i = 0; i < this.hash.length; i++) if ((this.hash[i] ^ pwdHash[i]) != 0) return false;
    return this.hash.length == pwdHash.length;
  }

  private byte[] genHash(String in, byte[] salt) {
    try {
      return SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1")
          .generateSecret(new PBEKeySpec(in.toCharArray(), salt, 65536, 128))
          .getEncoded();
    } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
      throw new RuntimeException(e.getMessage());
    }
  }
}
