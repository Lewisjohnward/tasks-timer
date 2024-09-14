# Todo
- [ ] Create Thumbnail
- [ ] Make Timer title enum a string resource
- [ ] Shouldn't Timer composable be a Row and not a Column?
- [ ] Implement Icon selection when creating a board
- [ ] Allow arbitrary selection of timers
- [ ] Hide board menu when no boards
- [ ] Implement rename board
- [ ] Add sounds

# Done
- [x] Timer menu implement edit timer, delete timer

# Bugs
- [ ] Delete last board then add timer, new board isn't loaded (works if delete last then delete again)
- [ ] When first fresh initialise of app database initialisation happens after screen so default board not loaded (key2= uistate.boards) fixes it but causes deletion bug