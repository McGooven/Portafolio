//Navbar
var scrollPos;
setInterval(() => {
    scrollPos = $(window).scrollTop();
    if (scrollPos > 30){
        $('#navStatic').css('display','none');
        $('#navFixed').css('display','flex');
        $('#upButton').css('display','flex');
        animateSlideInDown('#navFixed');
        animateBounceInUp('#upButton','animate__bounceInUp');
    }else if (scrollPos < 30){
        $('#navStatic').css('display' , 'flex');
        $('#navFixed').css('display' , 'none');
        $('#upButton').css('display','none');
    }
}, 300);

function animateSlideInDown(id) {
    $(id).addClass('animate__animated');
    $(id).addClass('animate__slideInDown');
    $(id).addClass('animate__faster');

    let element = document.querySelector(id);
    element.addEventListener('animationend', () => {
        $(id).removeClass(['animate__animated','animate__slideInDown','animate__faster'])
    });
}

function animateBounceInUp(id) {
    $(id).addClass('animate_animated');
    $(id).addClass('animate__bounceInUp');
    $(id).addClass('animate__faster');

    let element = document.querySelector(id);
    element.addEventListener('animationend', () => {
        $(id).removeClass(['animate__animated','animate__bounceInUp','animate__faster'])
    });

}
