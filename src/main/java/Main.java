import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import modelo.Post;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws UnirestException, IOException {

        String opciones = "";
        System.out.println("SocialBuddyREST client.\nElija la opcion deseada: ");
        while (true){
            menu();
            Scanner in = new Scanner(System.in);
            opciones = in.nextLine();

            switch (opciones){
                case "1" :{
                    getPostsUsuario();
                    break;
                }
                case "2":{
                    postear();
                    break;
                }
                case "e":{
                    System.out.println("Gracias por usar el SocialBuddyREST client");
                    System.exit(1);
                }
                default:{
                    System.out.println("Opcion no valida");
                    menu();
                }


            }
        }



        /*
        JSONObject j = new JSONObject();
        System.out.println("Agregar nuevo estudiante: \nCorreo: ");
        String correo = in.nextLine();
        j.put("correo",correo);
        System.out.println("\nCarrera: ");
        String carrera = in.nextLine();
        j.put("matricula",17);
        j.put("carrera",carrera);
        System.out.println("\nNombre: ");
        String nombre = in.nextLine();
        j.put("nombre",nombre);

        HttpResponse<JsonNode> jsonResponse2 = Unirest.post("http://localhost:4567/rest/estudiantes/")
                .header("accept", "application/json")
                .header("Content-Type", "application/json")
                .body(j)
                .asJson();

        System.out.println("Se guardo el estuidante: " + jsonResponse2.getBody());
        */
    }

    static void menu(){

        System.out.println("\n1)Cargar posts de un usuario." +
                "\n2)Crear un post" +
                "\ne)Salir\n");

    }

    static void getPostsUsuario() throws IOException, UnirestException {

        System.out.println("Usuario a consultar: ");
        Scanner in = new Scanner(System.in);

        String s = in.nextLine();

        HttpResponse<JsonNode> jsonResponse3 = Unirest.get("http://localhost:4567/posts/"+s).asJson();

        if(!jsonResponse3.getBody().toString().equalsIgnoreCase("[]")){
            com.fasterxml.jackson.databind.ObjectMapper objectMapper = new com.fasterxml.jackson.databind.ObjectMapper();

            List<Post> posts = objectMapper.readValue(
                    jsonResponse3.getBody().toString(),
                    objectMapper.getTypeFactory().constructCollectionType(
                            List.class, Post.class));

            System.out.println("************************************************");
            for (Post post : posts) {
                System.out.println("ID: " + post.getId());
                System.out.println("Texto del post: " + post.getTexto());
                System.out.println("Fecha posteado: " + post.getTiempo());
                System.out.println("************************************************");
            }
        }else{
            System.out.println("\n1Usuario no encontrado");
        }



    }

    static void postear() throws UnirestException {

      //  File file = new File ("a enviar/test.jpg").getAbsoluteFile();

        System.out.println("Credenciales del usuario.\nUsername o email: ");
        Scanner in = new Scanner(System.in);
        String s = in.nextLine();
        System.out.println("Password: ");
        String s2 = in.nextLine();

        HttpResponse<String> jsonResponse = Unirest.post("http://localhost:4567/autenticar")
                .field("username",s)
                .field("password",s2)
                .asString();

        System.out.println(jsonResponse.getBody());
        if(jsonResponse.getBody().equalsIgnoreCase("Error, revisar datos")){
            System.out.println("La combinacion de nombre de usuario y contraseña no produjo un usuario");
        }else{
            System.out.println("Texto del post: ");
            String texto = in.nextLine();
            System.out.println("Path imagen a postear (opcional): ");
            String imgPath = in.nextLine();
            if(imgPath.equalsIgnoreCase("")){
                HttpResponse<String> jsonResponse3 = Unirest.post("http://localhost:4567/crear/"+s)
                        .field("texto",texto)
                        .field("imagen","")
                        .asString();

                System.out.println(jsonResponse3.getBody());
            }else{
                HttpResponse<String> jsonResponse3 = Unirest.post("http://localhost:4567/crear/"+s)
                        .field("texto",texto)
                        .field("imagen",new File(imgPath))
                        .asString();

                System.out.println(jsonResponse3.getBody());
            }

        }


    }

    static Timestamp getFechaActual(){
        Date date= new Date();

        long time = date.getTime();
        return new Timestamp(time);
    }


}