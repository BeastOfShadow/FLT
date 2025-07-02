# 🧠 MiniLang AC/DC Compiler  
**A compiler for two toy languages: AC (an imperative C-like language) and DC (a stack-based postfix language).**

![Java](https://img.shields.io/badge/Java-17-blue) ![Platform](https://img.shields.io/badge/platform-CLI-lightgrey) ![License](https://img.shields.io/badge/License-MIT-green) ![Status](https://img.shields.io/badge/status-In%20Development-orange) ![Made with ❤️](https://img.shields.io/badge/made%20with-%E2%9D%A4-red)

---

**AC/DC Compiler** is a two-part compiler project developed for educational purposes. It supports two minimalistic languages:
- **AC**: An imperative toy language with expressions, assignments, control flow, and I/O.
- **DC**: A stack-based postfix language inspired by Unix’s `dc`, using reverse Polish notation.

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

## 🌤 Supported Languages

| Language | Description                                                        |
| -------- | ------------------------------------------------------------------ |
| **AC**   | C-like imperative language with variables, operators, control flow |
| **DC**   | Postfix (stack-based) expression language (like Unix `dc`)         |

---

## 📄 AC Language Overview

### ✍️ Syntax (AC)

```ac
x = 3 + 4;
y = x * 2;
print y;
```

### 🎯 Features (AC)

- Variables and assignments
- Arithmetic expressions: `+`, `-`, `*`, `/`
- Print statements: `print x;`
- Semi-colon terminated statements

---

## 📄 DC Language Overview

### ✍️ Syntax (DC)

```dc
3 4 + p
```

### 🎯 Features (DC)

- Postfix notation (RPN)
- Stack operations: push, pop, print
- Arithmetic operations: `+`, `-`, `*`, `/`
- `p` = print top of the stack
- `n` = pop and discard top

### ✅ Example:

```dc
5 1 2 + 4 * + 3 - p
```

Interpreted as:

- Push `5`
- Push `1`, `2` → `1 2 +` → `3`
- `3 4 *` → `12`
- `5 12 +` → `17`
- `17 3 -` → `14`
- `p` → prints `14`

---

## Tokens and Patterns

| Token | Pattern | Class |
|------|-------------|-------------|
| `INT` | `[0-9]+` |   Constant/Litteral  |
| `FLOAT` | `[0-9]+.([0-9]{0,5})` |   Constant/Literal  |
| `ID` | `[a-z][a-z0-9]*` |   Identificator  |
| `TYINT` | `int` |   Key Word  |
| `TYFLOAT` | `float` |   Key Word  |
| `PRINT` | `print` |   Key Word  |
| `OP_ASSIGN` | `+= \| -=  \| *= \| /=` |   Operators  |
| `ASSIGN` | `=` |   Operator  |
| `PLUS` | `+` |   Operator  |
| `MINUS` | `-` |   Operator  |
| `TIMES` | `*` |   Operator  |
| `DIVIDE` | `/` |   Operator  |
| `SEMI` | `;` |   Delimiter  |
| `EOF` | `(char) -1` |   End Input  |

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
│   ├── eception/         # Custom exception
│   ├── parser/           # Parser and grammar implementation
│   ├── scanner/          # Tokenizer and token types
│   ├── symbolTable/      # Type checking, symbol table
│   ├── test/             # Unit tests
│   ├── token/            # Token structure and type enums
│   └── visitor/          # Visitor pattern for AST traversal and interpretation
│
└── README.md               
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
- **Cosimo Daniele** – [GitHub](https://github.com/The-Forest03)

---

## 📄 License

This project is licensed under the [MIT License](LICENSE).
