package com.example.application.views;

import ai.peoplecode.OpenAIConversation;
import io.github.cdimascio.dotenv.Dotenv;

import java.util.ArrayList;

public class Game1 {
    private ArrayList<City1> cities;
    private OpenAIConversation conversation;
    private Scenario1 scenario;
    //where does the scenario get created?

    public Game1(OpenAIConversation conversation, Scenario1 scenario) {
        this.conversation = conversation;
        this.scenario = scenario;
        this.cities = initializeCities();
    }

    // Initialize the cities with generated conversations
    private ArrayList<City1> initializeCities() {
        ArrayList<City1> cities = new ArrayList<>();
        cities.add(createCity("Casablanca"));
        cities.add(createCity("Marrakech"));
        cities.add(createCity("Fes"));
        cities.add(createCity("Tangiers"));
        cities.add(createCity("Rabat"));
        return cities;
    }

    // Create a city with market and cafe conversations generated by OpenAI
    private City1 createCity(String cityName) {
        String marketConversation = generateMarketConversation(cityName);
        String cafeConversation = generateCafeConversation(cityName);
        return new City1(cityName, marketConversation, cafeConversation);
    }

    // Generate a market conversation for a specific city using OpenAI
    private String generateMarketConversation(String cityName) {
        String question = "Imagine you are a merchant in a market in " + cityName + ".  A random person (the user) will visit you in the market to ask you if you have seen "
                + scenario.getCriminal() + ", but they dont know who it is yet, they only have small details.  Generate clues about " + scenario.getCriminal() + "to share with a potential user, but dont add actual conversation with them." +
                "  For example, clues for Che Guevara would be \"Vendor mentions seeing a man with a Cuban cigar\", \"Saw a man in a beret\", \"Discussion about Bolivia and Cuba\", \"A man bought revolutionary pamphlets\".";
        return conversation.askQuestion("You are a merchant in " + cityName, question);
    }

    private String generateCafeConversation(String cityName) {
        String conversation = "";

        // Provide set conversation content for each city
        switch (cityName) {
            case "Casablanca":
                conversation = "The café owner mentions that the person in question was intrigued by the tales of a city known for its red sandstone buildings, vibrant souks, and a famous square filled with storytellers and performers.";
                break;
            case "Marrakech":
                conversation = "The café owner tells you stories of a city known for its ancient walled medina, a place where time seems to stand still, with a labyrinth of narrow streets, stunning madrasas, and the world's oldest university.";
                break;
            case "Fes":
                conversation = "The café owner shares that the person in question seemed intrigued by a city where the Mediterranean meets the Atlantic, a place known for its blend of cultures and historical influences, where the sea meets the sky.";
                break;
            case "Tangiers":
                conversation = "The café owner describes a city where modern and traditional cultures coexist, a city with a blue-washed Kasbah and a blooming art scene, nestled by the Atlantic coast.";
                break;
            case "Rabat":
                conversation = "The café owner says: 'The person in question was fascinated by an old proverb: Every road leads to the end of the world. It seemed he found what he was looking for right here, where history and contemporary art meet.'";
                break;
            default:
                conversation = "The café owner doesn't seem to know where the person might be heading next.";
                break;
        }

        return conversation;  // Return the built-in conversation content based on the city
    }
    // Generate a cafe conversation for a specific city using OpenAI
//    private String generateCafeConversation(String cityName) {
//        // Determine the next city based on the current city
//        String nextCity = "";
//
//        switch (cityName) {
//            case "Casablanca":
//                nextCity = "Marrakech";
//                break;
//            case "Marrakech":
//                nextCity = "Fes";
//                break;
//            case "Fes":
//                nextCity = "Tangiers";
//                break;
//            case "Tangiers":
//                nextCity = "Rabat";
//                break;
//            case "Rabat":
//                nextCity = "nowhere, you are at the final destination.";  // End of journey, no next city
//                break;
//            default:
//                nextCity = "unknown city";  // Just in case a city name is not recognized
//                break;
//        }
//
//        // Generate the conversation using the current city and the next city clue
//        String question = "Imagine you are a cafe owner in " + cityName + ". A random person (the user) will visit your cafe to ask if you have seen "
//                + scenario.getCriminal() + ", but they don't know who it is yet; they only have small details. "
//                + "Give them a clue about the next city they should travel to, which is " + nextCity + ", but do not say what the next city is.  You must give a clue to " + nextCity + "!  Do not say the person was happy to stay in current city";
//
//        return conversation.askQuestion("You are a local in the cafe in " + cityName, question);
//    }


    // Get the list of cities
    public ArrayList<City1> getCities() {
        return cities;
    }

    // Print conversations for testing
    public void printConversations() {
        for (City1 city : cities) {
            System.out.println("City: " + city.getName());
            System.out.println("Market Conversation: " + city.getMarketConversation());
            System.out.println("Cafe Conversation: " + city.getCafeConversation());
        }
    }

    public static void main(String[] args) {
        Dotenv dotenv = Dotenv.load();

        String dbHost = dotenv.get("DB_HOST");
        String dbUser = dotenv.get("DB_USER");
        String dbPassword = dotenv.get("DB_PASSWORD");

        

        // Create the conversation
        OpenAIConversation conversation = new OpenAIConversation(dbPassword, "gpt-4o");

        // Create a new Scenario using the OpenAI conversation
        Scenario1 scenario = new Scenario1(conversation);
        System.out.println("scenario created");

        // Create a new game using the Scenario information
        Game1 game = new Game1(conversation, scenario);

        // Print the generated conversations for testing
        game.printConversations();
    }
}

