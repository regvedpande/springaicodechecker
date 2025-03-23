
# AI Code Checker

A web application that uses AI to analyze and debug code snippets, providing suggestions for improvement. Built with Java, Spring Boot, and the Gemini API, this project helps developers improve their code through real-time feedback.

## Table of Contents
- [Features](#features)
- [Tech Stack](#tech-stack)
- [Setup](#setup)
- [Usage](#usage)
- [Contributing](#contributing)
- [Contact](#contact)
- [License](#license)

## Features
- Input code snippets via a web form.
- Analyze code using the Gemini API for AI-powered feedback.
- View suggestions for improvement in a clean, user-friendly interface.
- Built with reactive programming for efficient API calls.
- Simple and modern UI with Thymeleaf templating.

## Tech Stack
- **Backend**: Java, Spring Boot
- **Frontend**: Thymeleaf
- **API Integration**: WebClient, Gemini API
- **Reactive Programming**: Project Reactor
- **Logging**: SLF4J

## Setup
Follow these steps to set up the project locally:

### Prerequisites
- Java 17 or higher
- Maven
- An IDE (e.g., IntelliJ IDEA)
- A Gemini API key (optional for full functionality; replace the placeholder in `CodeCheckerService.java`)

### Steps
1. **Clone the Repository**:
   ```bash
   git clone https://github.com/regvedpande/springaicodechecker.git
   cd springaicodechecker
   ```

2. **Install Dependencies**:
   ```bash
   mvn clean install
   ```

3. **Configure the Gemini API Key**:
   - Open `src/main/java/com/example/codechecker/service/CodeCheckerService.java`.
   - Replace the `API_KEY` placeholder with your Gemini API key:
     ```java
     private final String API_KEY = "your-gemini-api-key-here";
     ```
   - Note: The current API key in the code may not work due to a `400 Bad Request` error. For a demo, the project uses a placeholder response.

4. **Run the Application**:
   ```bash
   mvn spring-boot:run
   ```
   Alternatively, run `CodecheckerApplication.java` in your IDE.

5. **Access the App**:
   - Open your browser and go to `http://localhost:8080`.

## Usage
1. Navigate to `http://localhost:8080`.
2. Paste a code snippet into the textarea (e.g., a Java program).
3. Click "Check Code" to see AI-generated feedback.
4. View the suggestions below the form.

**Example Input**:
```java
public class Test {
    public static void main(String[] args) {
        System.out.println("Hello, World!");
    }
}
```

**Example Output** (with placeholder response):
```
Received code: [your code]
This is a placeholder response.
```

## Contributing
Contributions are welcome! If you’d like to contribute:
1. Fork the repository.
2. Create a new branch (`git checkout -b feature/your-feature`).
3. Make your changes and commit (`git commit -m "Add your feature"`).
4. Push to your branch (`git push origin feature/your-feature`).
5. Open a pull request.

## Contact
For questions or feedback, reach out to me at:  
📧 [regregd@outlook.coim](mailto:regregd@outlook.coim)  
🌐 [GitHub](https://github.com/regvedpande)

## License
This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.
```

---

### Steps to Update the README on GitHub

1. **Update the `README.md` File**:
   - Open `README.md` in your project directory (`C:\Users\regre\Downloads\codechecker\README.md`).
   - Replace its content with the updated version above.
   - Save the file.

2. **Add and Commit the Changes**:
   ```bash
   git add README.md
   git commit -m "Remove project structure section from README"
   ```

3. **Push to GitHub**:
   ```bash
   git push origin main
   ```
   - If your default branch is `master` instead of `main`, replace `main` with `master`.

4. **Verify on GitHub**:
   - Go to `https://github.com/regvedpande/springaicodechecker` in your browser.
   - Confirm that the updated README is displayed without the "Project Structure" section.

---

### Additional Notes
- **Placeholder Image**: The placeholder image is still included. If you have a screenshot of your app, you can add it to an `images` folder in your repo and update the link in the README.
- **License File**: If you haven’t added the `LICENSE` file yet, you can create one with the MIT License content (as shown in the previous response) and push it to your repo.
- **Gemini API Issue**: The README still notes the placeholder response due to the `400 Bad Request` error with the Gemini API. If you’d like to resolve this, please share the output of the `curl` command:
  ```bash
  curl "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.0-flash:generateContent?key=AIzaSyBG46HbZ0O8Sp6UVHC7XaFvF3XxNbRi-2I" \
  -H 'Content-Type: application/json' \
  -X POST \
  -d '{
    "contents": [{
      "parts":[{"text": "Explain how AI works"}]
    }]
  }'
