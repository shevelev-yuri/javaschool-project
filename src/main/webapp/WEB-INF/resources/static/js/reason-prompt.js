function getReason() {
    var input = document.getElementById('reason');
    console.debug('Old value: ' + input.value);
    var inputVal = prompt("Please specify cancellation reason");
    if (inputVal == null || inputVal === "") {
        return false;
    } else {
        input.value = inputVal;
        console.debug('New value: ' + input.value);
        return true;
    }
}