# JSON Requests, Responses

This document provides an overview of how the client and server communicate. Most of the JSON objects below describe the request to the server. For our purposes here, the server acts as a "black box" since the relevant information is what is sent and received from the server, not the server's request processing logic.

## Login
Upon login, the entered `nickname` and `password` are sent to the server,. If a match for both `nickname` and `password` is found in the database, the server sends **true** back to the client to indicate that the login was successful.

```json
{
  "nickname"           : "junglePlayer1",
  "password"           : "spaghetti"
}
```

## Register
When a user registers, their `nickname`, `password`, `verifyPassword`, and `email` are sent to the server to create an account. The following criteria must be met for an account to be created:
- `nickname` is not already taken, fits within our constraints (i.e length, characters)
- `password` == `verifyPassword`, both fit within our constraints (i.e length, characters)
- `email` is correctly formatted

If all the above criteria are met, the server sends **true** back to the client to indicate success.
```json
{
  "nickname"           : "junglePlayer1",
  "password"           : "spaghetti",
  "verifyPassword"     : "spaghetti",
  "email"              : "email@gmail.com"
}
```

## Profile
The user's profile (`nickname`, `password`, `ratio`, `wins`, `losses`, `email`) is sent to the server from the Profile page's state.

`retrieveProfile`: Upon viewing the profile page, a null request is sent and the response populates the client-side state with profile information.

```json
{
  "nickname"           : "junglePlayer1",
  "password"           : "",
  "ratio"              : null,
  "wins"               : null,
  "losses"             : null,
  "email"              : ""
}
```

`unregister`: The server simply nullifies the profile attributes. The unregistered profile is then removed from the database and *true* is sent back to indicate success.

```json
{
  "nickname"           : "junglePlayer1",
  "password"           : "spaghetti",
  "ratio"              : 0.5,
  "wins"               : 1,
  "losses"             : 1,
  "email"              : "email@gmail.com"
}
```

`updateProfile`: The server updates the values in the database that the user wants to be change. Only `password` and `email` can be updated.

```json
{
  "nickname"           : "junglePlayer1",
  "password"           : "spaghetti",
  "ratio"              : 0.5,
  "wins"               : 1,
  "losses"             : 1,
  "email"              : "email@gmail.com",
  "newPassword"        : "tortellini",
  "newEmail"           : "email@yahoo.com"
}
```

## Invite
When a user wants to invite another user to play, they must search for them or get a random opponent.

`searchPlayer`: returns the profile of the player with the specified nickname.

`getRandomPlayer`: returns a random profile from the database.

`invitePlayer`: returns a pending match with *playerBlue being the invitee, and playerRed being the inviter*.

`declineMatch`: deletes the pending match and returns *true* on success.

*The following JSON object represents a general outline of the invitation data flow:*

```json
{
  "opponentFound"      : false,
  "playerRed"          : "junglePlayer1",
  "playerBlue"         : "",
  "nickname"           : "myOpponent123",
}
```

## Home
This page displays all of a user's current, pending, and past games and allows them to load any of them.

`retrieveMatches`: returns every match involving the current user. The matches are sorted into three categories by `status`, which can be one of "Active", "Pending", or "Finished".

```json
{ "nickname": "junglePlayer1" }
```

`retrieveMatch`: returns a specific match (game state) by `gameID`.

```json
{ "gameID": "303" }
```

## GamePage
When a game is opened, the server sends the current gamestate in `board` as a 9x7 two-dimensional array of Piece objects. Each object can be either `null` if the space is empty, or the residing piece with the board it belongs to and the following information:

**Piece Object** - `board[0][0]`
```json
{
  "board"              : { BoardObject },
  "pieceColor"         : "red",
  "rank"               : 7,
  "row"                : 0,
  "col"                : 0,
  "name"               : "lion",
  "isTrapped"          : false,
  "legalMoves"         : ["01", "10"],
  "redTraps"           : ["02", "13", "04"],
  "blueTraps"          : ["82", "73", "84"],
  "waterTiles"         : ["31", "32", "41", "42", "51", "52", "34", "35", "44", "45", "54", "55"]
}
```

The following game board format is used for a few different request types: `newMatch`, `updateMatch`, `forefeitMatch`.

`newMatch`: Initializes a new game state.

`updateMatch`: Update game state after a move.

`forfeitMatch`: Set status to "Finished", and the player who didn't forfeit becomes the winner.

**Board Object** (Formulaic JSON - value types are implied by highlighted text):

```json
{
  "board"              : [
                            [
                               {
                                  "name": String,
                                  "player": String,
                                  "legalMoves": [
                                      {
                                         "row": int,
                                         "col": int
                                      },
                                      {
                                         "row": int,
                                         "col": int
                                      }
                                  ]
                               },
                               { PieceObject },
                               { PieceObject },
                               "..."
                            ],
                            [
                               { PieceObject },
                               { PieceObject },
                               { PieceObject },
                               "..."
                            ],
                            "..."
                         ],
  "winner"             : String,
  "playerBlue"         : String,
  "playerRed"          : String,
  "playerTurn"         : String,
  "move"               : {
                            "row": int,
                            "col": int,
                            "toRow": int,
                            "toCol": int
                         }
}
```


