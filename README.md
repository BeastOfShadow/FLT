# 🧠 MiniLang Compiler  
**A simple compiler for a minimal programming language designed for didactic purposes.**

![Java](https://img.shields.io/badge/Java-17-blue) ![Platform](https://img.shields.io/badge/platform-CLI-lightgrey) ![License](https://img.shields.io/badge/License-MIT-green) ![Status](https://img.shields.io/badge/status-In%20Development-orange) ![Made with ❤️](https://img.shields.io/badge/made%20with-%E2%9D%A4-red)

---

**MiniLang Compiler** is a compiler written in Java for a small, educational language supporting basic statements, arithmetic expressions, and control flow. It provides a full compilation pipeline including lexical, syntactic, and semantic analysis, along with an intermediate representation.

---

## 🚀 Features

- 🔍 **Lexical Analysis** with custom tokenizer
- 🧾 **Parser** with recursive descent for grammar rules
- 🧠 **Abstract Syntax Tree (AST)** generation  
- 🛠️ **Semantic checks** for identifiers and types  
- 🔄 **Support for combined assignment operators** (`+=`, `-=`, etc.)  
- 🖨 **Print statement support**  
- ❌ **Custom exception handling** for syntactic and lexical errors  
- 🧪 **JUnit testing** for core components  

---

## 📜 Supported Grammar

| Rule | Description |
|------|-------------|
| `ID = EXP;` | Simple assignment |
| `ID += EXP;` | Compound assignment |
| `print ID;` | Print statement |
| `EXP` | Arithmetic expressions with `+`, `-`, `*`, `/` and precedence |
| `ID`, `NUM` | Identifiers and numeric literals |

---

## 🧑‍💻 Tech Stack

- **Language**: Java 17  
- **Build Tool**: Maven  
- **Testing**: JUnit 5  
- **IDE Recommended**: IntelliJ IDEA / VS Code with Java plugins  

---

## 📁 Project Structure

```
minilang-compiler/
│
├── src/
│   ├── ast/              # AST node definitions
│   ├── parser/           # Parser and grammar implementation
│   ├── lexer/            # Tokenizer and token types
│   ├── semantic/         # Type checking, symbol table
│   ├── main/             # Entry point for compilation
│   └── test/             # Unit tests
│
├── README.md
└── pom.xml               # Maven configuration
```

---

## ⚙️ Getting Started

Follow these steps to run the project locally.

### 📥 1. Clone the repository

```bash
git clone https://github.com/your-username/minilang-compiler.git
cd minilang-compiler
```

### 🔧 2. Build the project

```bash
mvn clean install
```

### 🚀 3. Run the compiler

```bash
mvn exec:java -Dexec.mainClass="compiler.Main" -Dexec.args="path/to/file.ml"
```

> Replace `path/to/file.ml` with the path to your MiniLang source file.

---

## 🧪 Example Input

```c
x = 5;
x += 2;
print x;
```

Will produce a valid AST and simulate print output: `7`.

---

## 🧠 Developer Notes

- The parser uses a **recursive descent** strategy.
- Statements are matched using a **predictive lookahead** with `peekToken()`.
- AST nodes like `NodeAssign`, `NodePrint`, and `NodeBinOp` are used to represent program structure.
- Compound operators like `+=` are transformed internally to binary operations within assignments.

---

## 🙏 Credits

This compiler was developed as an academic project by:

- **Simone Negro** – [GitHub](https://github.com/BeastOfShadow) | [LinkedIn](https://www.linkedin.com/in/negro-simone-babb88238/)

---

## 📄 License

This project is licensed under the [MIT License](LICENSE).
