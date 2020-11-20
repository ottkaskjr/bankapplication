console.log("JS loaded!")
let medias = document.getElementsByClassName("media");
//setTimeout(() => {
  let carouselInner = document.querySelector(".carousel-inner")
  console.log(carouselInner);
  let first = true;
  for(let media of medias){
    //console.log(media);
    let src = media.getAttribute('src');
    let dataID = media.getAttribute('data-id')
    let DIV = document.createElement("DIV");
    DIV.id = dataID;
    if (first){
      DIV.classList.add("carousel-item", "active");
      
    } else {
      DIV.classList.add("carousel-item");
    }
    first = false;
    let IMG = document.createElement("IMG");
    IMG.classList.add("d-block", "w-100")
    IMG.setAttribute("src", src);
    DIV.append(IMG)
    carouselInner.append(DIV);

  }
//}, 1000)

let mediaA = document.getElementsByClassName("open-slideshow");
for(let a of mediaA){
  a.addEventListener('click', function (){
    removeActives(document.getElementsByClassName('carousel-item'))
    // set new image active
    let id = this.getAttribute('data-id');
    document.getElementById(id).classList.add('active')
  })
}

let removeActives = function (images){
  for(let image of images){
    image.classList.remove('active');
  }
}