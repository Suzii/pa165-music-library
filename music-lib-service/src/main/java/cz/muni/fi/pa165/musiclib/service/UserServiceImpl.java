 package cz.muni.fi.pa165.musiclib.service;

 import cz.muni.fi.pa165.musiclib.dao.UserDao;
 import cz.muni.fi.pa165.musiclib.entity.User;
 import cz.muni.fi.pa165.musiclib.exception.MusicLibDataAccessException;
 import org.springframework.stereotype.Service;

 import javax.crypto.SecretKeyFactory;
 import javax.crypto.spec.PBEKeySpec;
 import javax.inject.Inject;
 import javax.persistence.EntityExistsException;
 import javax.persistence.TransactionRequiredException;
 import java.math.BigInteger;
 import java.security.SecureRandom;
 import java.util.List;

/**
 * @author Martin Stefanko
 * @version 15/11/22
 */
 @Service
public class UserServiceImpl implements UserService {

    @Inject
    private UserDao userDao;

    @Override
    public void registerUser(User user, String encryptedPass) {
        if(user == null) {
            throw new IllegalArgumentException("user cannot be null");
        }
        if(encryptedPass == null) {
            throw new IllegalArgumentException("password cannot be null");
        }

        user.setPasswordHash(createHash(encryptedPass));
        try {
            userDao.create(user);
        } catch (EntityExistsException | IllegalArgumentException | TransactionRequiredException e) {
            throw new MusicLibDataAccessException("user create error");
        }
    }

    @Override
    public boolean authenticate(User user, String pass) {
        if(user == null) {
            throw new IllegalArgumentException("user cannot be null");
        }
        return validatePassword(pass, user.getPasswordHash());
    }

    @Override
    public boolean isAdmin(User user) {
        return findUserById(user.getId()).isAdmin();
    }

    @Override
    public User findUserById(Long id) {
        try {
            return userDao.findById(id);
        } catch (IllegalArgumentException ex) {
            throw new MusicLibDataAccessException("user findById error");
        }
    }

    @Override
    public User findUserByEmail(String email) {
        try {
            return userDao.findByEmail(email);
        } catch (IllegalArgumentException ex) {
            throw new MusicLibDataAccessException("user findByEmail error");
        }
    }

    @Override
    public List<User> getAllusers() {
        try {
            return userDao.findAll();
        } catch (IllegalArgumentException ex) {
            throw new MusicLibDataAccessException("find all users error");
        }
    }

    //see  https://crackstation.net/hashing-security.htm#javasourcecode
    private static String createHash(String password) {
        final int SALT_BYTE_SIZE = 24;
        final int HASH_BYTE_SIZE = 24;
        final int PBKDF2_ITERATIONS = 1000;
        // Generate a random salt
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[SALT_BYTE_SIZE];
        random.nextBytes(salt);
        // Hash the password
        byte[] hash = pbkdf2(password.toCharArray(), salt, PBKDF2_ITERATIONS, HASH_BYTE_SIZE);
        // format iterations:salt:hash
        return PBKDF2_ITERATIONS + ":" + toHex(salt) + ":" + toHex(hash);
    }

    private static byte[] pbkdf2(char[] password, byte[] salt, int iterations, int bytes) {
        try {
            PBEKeySpec spec = new PBEKeySpec(password, salt, iterations, bytes * 8);
            return SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256").generateSecret(spec).getEncoded();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static byte[] fromHex(String hex) {
        byte[] binary = new byte[hex.length() / 2];
        for (int i = 0; i < binary.length; i++) {
            binary[i] = (byte) Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16);
        }
        return binary;
    }

    private static String toHex(byte[] array) {
        BigInteger bi = new BigInteger(1, array);
        String hex = bi.toString(16);
        int paddingLength = (array.length * 2) - hex.length();
        return paddingLength > 0 ? String.format("%0" + paddingLength + "d", 0) + hex : hex;
    }

    /**
     * Compares two byte arrays in length-constant time. This comparison method
     * is used so that password hashes cannot be extracted from an on-line
     * system using a timing attack and then attacked off-line.
     *
     * @param a the first byte array
     * @param b the second byte array
     * @return true if both byte arrays are the same, false if not
     */
    private static boolean slowEquals(byte[] a, byte[] b) {
        int diff = a.length ^ b.length;
        for (int i = 0; i < a.length && i < b.length; i++)
            diff |= a[i] ^ b[i];
        return diff == 0;
    }

    public static boolean validatePassword(String password, String correctHash) {
        if(password==null) return false;
        if(correctHash==null) throw new IllegalArgumentException("password hash is null");
        String[] params = correctHash.split(":");
        int iterations = Integer.parseInt(params[0]);
        byte[] salt = fromHex(params[1]);
        byte[] hash = fromHex(params[2]);
        byte[] testHash = pbkdf2(password.toCharArray(), salt, iterations, hash.length);
        return slowEquals(hash, testHash);
    }
}
