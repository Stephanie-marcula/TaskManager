import java.io.*;
import java.util.*;

public class TaskManager {
    private static final String FILE_NAME = "tasks.txt";
    private static int score = 0;
    private static List<String> tasks = new ArrayList<>();

    public static void main(String[] args) {
        loadTasks();
        Scanner scanner = new Scanner(System.in);
        int choice;
        
        do {
            showMenu();
            choice = Integer.parseInt(scanner.nextLine());
            
            switch (choice) {
                case 1: 
                    addTask(scanner);
                    break;
                case 2:
                    listTasks(scanner);
                    break;
                case 3:
                    completeTask(scanner);
                    break;
                case 4:
                    removeTask(scanner);
                    break;
                case 5:
                    showLevel(scanner);
                    break;
                case 6:
                    System.out.println("Saindo... Até logo!");
                    saveTasks();
                    break;
                default:
                    System.out.println("Opção inválida.");
            }
        } while (choice != 6);
        
        scanner.close();
    }
    
    private static void showMenu() {
        System.out.println("\n=== Gerenciador de Tarefas ===");
        System.out.println("Pontuação: " + score);
        System.out.println("[1] Adicionar Tarefa");
        System.out.println("[2] Listar Tarefas");
        System.out.println("[3] Concluir Tarefa (+10 pts)");
        System.out.println("[4] Remover Tarefa");
        System.out.println("[5] Ver Nível");
        System.out.println("[6] Sair");
        System.out.print("Escolha uma opção: ");
    }
    
    private static void addTask(Scanner scanner) {
        String opcao;
        do {
            System.out.print("Digite a nova tarefa: ");
            String task = scanner.nextLine();
            tasks.add("[ ] " + task);
            saveTasks();
            System.out.println("Tarefa adicionada!");
            displayTasks();
            System.out.print("Digite 'S' para adicionar outra ou pressione Enter para voltar: ");
            opcao = scanner.nextLine();
        } while(opcao.equalsIgnoreCase("S"));
    }
    
    private static void listTasks(Scanner scanner) {
        displayTasks();
        System.out.print("Pressione Enter para voltar ao menu: ");
        scanner.nextLine();
    }

    private static void displayTasks() {
        if (tasks.isEmpty()) {
            System.out.println("Nenhuma tarefa encontrada.");
        } else {
            for (int i = 0; i < tasks.size(); i++) {
                System.out.println((i + 1) + ". " + tasks.get(i));
            }
        }
    }
    
    private static void completeTask(Scanner scanner) {
        displayTasks();
        if (tasks.isEmpty()) {
            System.out.print("Pressione Enter para voltar: ");
            scanner.nextLine();
            return;
        }
        System.out.print("Digite o número da tarefa concluída: ");
        int index = Integer.parseInt(scanner.nextLine()) - 1;
        if (index >= 0 && index < tasks.size()) {
            tasks.set(index, tasks.get(index).replace("[ ]", "[X]"));
            score += 10;
            saveTasks();
            System.out.println("Tarefa concluída! +10 pontos");
        } else {
            System.out.println("Número inválido.");
        }
        System.out.print("Pressione Enter para voltar: ");
        scanner.nextLine();
    }
    
    private static void removeTask(Scanner scanner) {
        displayTasks();
        if (tasks.isEmpty()) {
            System.out.print("Pressione Enter para voltar: ");
            scanner.nextLine();
            return;
        }
        System.out.print("Digite o número da tarefa a remover: ");
        int index = Integer.parseInt(scanner.nextLine()) - 1;
        if (index >= 0 && index < tasks.size()) {
            tasks.remove(index);
            saveTasks();
            System.out.println("Tarefa removida!");
        } else {
            System.out.println("Número inválido.");
        }
        System.out.print("Pressione Enter para voltar: ");
        scanner.nextLine();
    }
    
    private static void showLevel(Scanner scanner) {
        int level = score / 50 + 1;
        System.out.println("Seu nível: " + level);
        System.out.print("Pressione Enter para voltar: ");
        scanner.nextLine();
    }
    
    private static void saveTasks() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            writer.write(score + "\n");
            for (String task : tasks) {
                writer.write(task);
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Erro ao salvar tarefas.");
        }
    }
    
    private static void loadTasks() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            score = Integer.parseInt(reader.readLine());
            String line;
            while ((line = reader.readLine()) != null) {
                tasks.add(line);
            }
        } catch (IOException | NumberFormatException e) {
            System.out.println("Nenhuma tarefa encontrada.");
        }
    }
}
