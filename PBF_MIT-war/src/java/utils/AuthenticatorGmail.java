/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

/**
 *
 * @author USER
 */
public class AuthenticatorGmail extends Authenticator {

    @Override
    protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication("kennegervais@gmail.com", "G1234DiffoRodrigue@");
    }

}
