comment.txt - should end analysis immediately with success. No tokens should be returned.

empty.txt - same as comment.txt in regard to how the analyzer should react as the input file immediately starts with the end fo the file.

exclame.txt - should report a lexical error for the first character.
lexically-not-ok.txt - see readme under week 7 on Blackboard.

ok.txt - contains all of the provided lexically-valid files combined. Should output 406 tokens of the various types.

slash.txt - contains a lexically-valid token, the division symbol, and should output one token.

sym.txt - should output 15 tokens of reserved symbols with no value

under.txt - should report a lexical error, as no type of token can start with a character of an underscore. Identifiers can contain numbers and underscores, but can only start with a letter.
