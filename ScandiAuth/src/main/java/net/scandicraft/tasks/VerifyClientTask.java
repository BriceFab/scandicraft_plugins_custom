package net.scandicraft.tasks;

import net.scandicraft.config.ScandiCraftMultiplayer;
import net.scandicraft.http.HTTPClient;
import net.scandicraft.http.HTTPEndpoints;
import net.scandicraft.http.HTTPReply;
import net.scandicraft.http.HttpStatus;
import net.scandicraft.http.entity.VerifyTokenEntity;
import net.scandicraft.logs.LogManager;
import net.scandicraft.packets.client.CPacketAuthToken;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.util.concurrent.Callable;

public class VerifyClientTask implements Callable<VerifyClientResponse> {
    private final Player player;
    private final String hostname;

    public VerifyClientTask(final Player player, final String hostname) {
        this.player = player;
        this.hostname = hostname;
    }

    @Override
    public VerifyClientResponse call() throws Exception {
        LogManager.consoleInfo("Receive connection for " + player.getName() + " start checking");

        final String[] hostname = this.hostname.split(":")[0].split("\0");
        final String default_error = "Veuillez télécharger le launcher ScandiCraft (https://scandicraft-mc.fr/jouer) !";

        if (hostname.length == 2) {
            //contrôle l'auth key qui doit être passée dans l'hostname
            if (hostname[1].equals(ScandiCraftMultiplayer.AUTH_KEY)) {
                //attends 2 secondes pour la reception du token
                Thread.sleep(2000);
                final String token = CPacketAuthToken.getToken();
                LogManager.consoleWarn("token " + token);

                //si pas de token
                if (token == null || token.equals("") || token.length() <= 0) {
                    return new VerifyClientResponse(false, "Erreur d'authentification: aucun token");
                }

                //envoie un post sur l'API du site pour vérifier le token
                VerifyTokenEntity entity = new VerifyTokenEntity(player.getName(), player.getUniqueId().toString(), token);
                HTTPClient httpClient = new HTTPClient(token);
                HTTPReply httpReply = httpClient.post(HTTPEndpoints.VERIFY_TOKEN, entity);
//                    getLogger().info("VerifyToken Response: " + httpReply.getStatusCode() + " - " + httpReply.getJsonResponse());

                //traitement de la réponse http si code 200
                if (httpReply.getStatusCode() == HttpStatus.HTTP_OK) {
                    //si l'username et l'uuid sont les mêmes que la réponse
                    if (httpReply.getJsonResponse().get("username").getAsString().equals(player.getName()) && httpReply.getJsonResponse().get("uuid").getAsString().equals(player.getUniqueId().toString())) {
                        return new VerifyClientResponse(true, "Authentifié avec succès sur ScandiCraft (en %d ms) !");
                    } else {
                        return new VerifyClientResponse(false, "Username ou UUID invalides..");
                    }
                } else if (httpReply.getJsonResponse().get("error") != null) {
                    return new VerifyClientResponse(false, "Erreur d'authentification: " + httpReply.getJsonResponse().get("error"));
                }
            }
        }

        return new VerifyClientResponse(false, default_error);
    }
}
