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

Character to ignore: `' ', '\n', '\t', '\r'`.

Token format example: 

```
<TYINT,r:1><ID,r:1,tempa><SEMI,r:1>
<ID,r:2,tempa><ASSIGN,r:2><INT,r:2,5><SEMI,r:2>
<TYFLOAT,r:3><ID,r:3,tempb><ASSIGN,r:3><ID,r:3,tempa><DIVIDE,r:3>
<FLOAT,r:3,3.2><SEMI,r:2>
<ID,r:4,tempb><OP_ASSIGN,r:4,+=><INT,r:4,7><SEMI,r:4>
<PRINT,r:5><ID,r:5,tempb><SEMI,r:5><EOF,r:5>
```
---

## 📘 Predictive Parsing Table (Grammar)

This table outlines the predictive parsing rules for the AC language grammar.

| Num. | LHS   | RHS             | Predict Set                    |
|------|-------|------------------|--------------------------------|
| 0    | Prg   | DSs $            | { TYFLOAT, TYINT, ID, PRINT, EOF } |
| 1    | DSs   | Dcl DSs          | { TYFLOAT, TYINT }             |
| 2    | DSs   | Stm DSs          | { ID, PRINT }                  |
| 3    | DSs   | ϵ                | { EOF }                        |
| 4    | Dcl   | Ty id DclP       | { TYFLOAT, TYINT }             |
| 5    | DclP  | ;                | { SEMI }                       |
| 6    | DclP  | = Exp ;          | { ASSIGN }                     |
| 7    | Stm   | id opAss Exp ;   | { ID }                         |
| 8    | Stm   | print id ;       | { PRINT }                      |
| 9    | Exp   | Tr ExpP          | { ID, FLOAT, INT }             |
| 10   | ExpP  | + Tr ExpP        | { PLUS }                       |
| 11   | ExpP  | - Tr ExpP        | { MINUS }                      |
| 12   | ExpP  | ϵ                | { SEMI }                       |
| 13   | Tr    | Val TrP          | { ID, FLOAT, INT }             |
| 14   | TrP   | * Val TrP        | { TIMES }                      |
| 15   | TrP   | / Val TrP        | { DIVIDE }                     |
| 16   | TrP   | ϵ                | { MINUS, PLUS, SEMI }          |
| 17   | Ty    | float            | { TYFLOAT }                    |
| 18   | Ty    | int              | { TYINT }                      |
| 19   | Val   | intVal           | { INT }                        |
| 20   | Val   | floatVal         | { FLOAT }                      |
| 21   | Val   | id               | { ID }                         |
| 22   | Op    | =                | { ASSIGN }                     |
| 23   | Op    | opAss            | { OP_ASSIGN }                  |

To improve its readability:

```bnf
Prg  → DSs $

DSs  → Dcl DSs | Stm DSs | ε

Dcl  → Ty id DclP

DclP → ; | = Exp ;

Stm  → id opAss Exp ; | print id ;

Exp  → Tr ExpP

ExpP → + Tr ExpP | - Tr ExpP | ε

Tr   → Val TrP

TrP  → * Val TrP | / Val TrP | ε

Ty   → float | int

Val  → intVal | floatVal | id

Op   → = | opAss
```
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
https://github.com/BeastOfShadow/FLT.git
cd FLT
```

### 🔧 2. Build the project

```bash
mvn clean install
```

### 🚀 3. Run the compiler

There is no main entry point — only test files are provided to verify all functionalities. This is a necessary improvement.

---

## 🧪 Example Input AC

```c
x = 5;
x += 2;
print x;
```

Will produce a valid AST and simulate print output: `7`.

---

## 🧪 Example Input DC

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
