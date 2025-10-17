package com.example;

import java.util.Scanner;

import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.model.openai.OpenAiChatModelName;
import dev.langchain4j.chain.ConversationalChain;

public class OllamaCliRunner {

    // Timeout in seconds; set to 0 to disable
    private static final long TIMEOUT_SECONDS = 120;
    private static String OPENAI_API_KEY = "";

    public static void main(String[] args) {
        ChatModel model = OpenAiChatModel.builder()
                .temperature(0.8)
                .apiKey(OPENAI_API_KEY).modelName(OpenAiChatModelName.GPT_4_1_MINI)
                .build();

        ConversationalChain cc = ConversationalChain.builder()
                .chatModel(model).build();

        Scanner scanner = new Scanner(System.in);

        cc.execute(
                "Hello, let's play a text based game where we take the context from the witcher 3 game. You are an NPC in the game and I am Geralt of Rivia. You will respond to my prompts as if you are in the game world. Do not break character. if I annoy you with out of context questions or phrases for 3 times I should lose. I will give you more details about the situation in the next message.");

        cc.execute(
                "You and I are bargaining about the price for me killing a monster that is terrorizing a village. You are the village elder and I am Geralt of Rivia. You want me to kill the monster but you also want to pay me as little as possible. Respond to my prompts accordingly.");

        cc.execute(
                "Let's set the bar to start with you wanting to pay me only 100 coins but you don't want to say the actual number so you let make the first step. don't suggest numbers unless I convince you. if I annoy too much with high prices more than 3 times you will refuse to pay me and I will leave the village without killing the monster and I lose. If I manage to convince you to pay me a fair price within 3 tries I win and agree to kill the monster and then I win.");

        cc.execute(
                "Please reply with one of the following if I win say 'I will pay you what you want' and if I lose say 'This is too much, I will leave the village for good'.");

        cc.execute(
                "We will play this game in phases, the first phase is that I try to know how much you wanna pay before I say the first number, the second phase is where we actually bargain the price.");

        String openningPrompt = cc.execute(
                "Start with a message as an NPC to open the topic and give hints to the player what to do in every phase, if the player exceeds 5 questions without knowing what to the player loses. if I get the base price you wanna pay we move to the second phase. Reply with the opening message now. Let's begin!");

        System.out.println(
                "You are Geralt of Rivia, the most ruthless witcher ever. you need to get yourself some coins so you can continue your journey to find Siri!");

        System.out.println(
                "An Elder man is asking you to kill a monster that is terrorizing his village.He is asking you to kill the monster but doesn't seem like an easy negotiation. Negotiate the price with him.");

        System.out.println("Try to know how much he can pay first before bargaining");

        System.out.println(openningPrompt);
        while (true) {
            System.out.println(">>");
            String prompt = scanner.nextLine();

            String promptResult = cc.execute(prompt);
            if (promptResult.contains("I will pay you what you want")) {
                System.out.println(
                        "\n You won! The village elder agrees to your price and you set off to kill the monster.");
                break;
            } else if (promptResult.contains("This is too much, I will leave the village for good")) {
                System.out.println(
                        "\n You lost! The village elder refuses to pay your price and you leave the village without killing the monster.");
                break;
            }
            System.out.println("\n" + promptResult);
        }
    }
}