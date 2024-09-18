# Todo
- [ ] Create Thumbnail
- [ ] Make Timer title enum a string resource
- [ ] Shouldn't Timer composable be a Row and not a Column?
- [ ] Implement Icon selection when creating a board
- [ ] Implement rename board
- [ ] Implement duplicate timer
- [ ] Add sounds
- [ ] Add vibration
- [ ] Pass data class in navigation instead of strings

# Done
- [x] Timer menu implement edit timer, delete timer
- [x] Allow arbitrary selection of timers
- [x] Hide board menu when no boards

# Bugs
- [ ] Delete last board then add timer, new board isn't loaded (works if delete last then delete again)
- [ ] When first fresh initialise of app database initialisation happens after screen so default board not loaded (key2= uistate.boards) fixes it but causes deletion bug