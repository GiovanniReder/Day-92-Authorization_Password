package giovanni.security.security;

import giovanni.security.Employees.Employees;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JWTTools {

    private  String secret;

    public JWTTools(@Value("${jwt.secret}") String secret) {
        this.secret = secret;
    }

    public String createToken(Employees employee){ // Dato l'utente generami un token per esso
      return   Jwts.builder().issuedAt(new Date(System.currentTimeMillis())) // <-- Data di emissione del token (IAT - Issued AT), va messa in millisecondi
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 7))// <-- Data di scadenza del token (Expiration Date), anch'essa in millisecondi, quindi prendi quella messa dentro issued at e aggiungi
                .subject(String.valueOf(employee.getId()))// <-- Subject, ovvero a chi appartiene il token (ATTENZIONE A NON INSERIRE LE PASSWORD O ALTRI DATI SENSIBILI QUA DENTRO!!!!!!)
                .signWith(Keys.hmacShaKeyFor(secret.getBytes())) // <-- Con quest firmo il token, passandogli il SEGRETO , questo segreto (https://miniwebtool.com/it/random-string-generator/ SCEGLI 40 CARATTERI) va nel file env e lo passi nel costruttore tramite @Value
                .compact();
    }





    public void verifyToken(String token){
        try {
            // Dato un token verificami se è valido
       Jwts.parser().verifyWith(Keys.hmacShaKeyFor(secret.getBytes())).build().parse(token);
        } catch (Exception ex){
            throw new UnauthorizedException("Problemi col token, effettua di nuovo il login");
        }}

    public String extractIdFromToken(String token){
        return Jwts.parser().verifyWith(Keys.hmacShaKeyFor(secret.getBytes())).build()
                .parseSignedClaims(token).getPayload().getSubject();
    }

    }

