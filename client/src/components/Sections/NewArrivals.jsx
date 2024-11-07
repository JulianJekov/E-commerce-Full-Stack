import React from 'react'
import SectionHeading from './SectionHeading'
import Card from '../Card/Card'
import Jeans from '../../assets/imgs/jeans.jpg'
import Shirts from '../../assets/imgs/shirts.jpg'
import Tshirts from '../../assets/imgs/tshirts.jpeg'
import Dresses from '../../assets/imgs/dresses.jpg'
import Kurtis from '../../assets/imgs/kurtis.jpg'
import Joggers from '../../assets/imgs/joggers.jpg'
import Carousel from 'react-multi-carousel'
import { responsive } from '../../utils/responsive'


const items = [{
    'title':'Jeans',
    imagePath:Jeans
},{
    'title':'Shirts',
    imagePath:Shirts
},{
    'title':'Tshirts',
    imagePath:Tshirts
},{
    'title':'Dresses',
    imagePath:Dresses
},{
    'title':'Kurtis',
    imagePath:Kurtis
},{
    'title':'Joggers',
    imagePath:Joggers
}]

const NewArrivals = () => {
  return (
    <>
    <SectionHeading title={'New Arrivals'}/>
    <Carousel
        responsive={responsive}
        autoPlay={false}
        swipeable={true}
        draggable={false}
        showDots={false}
        infinite={false}
        >
        {items && items?.map((item,index)=> <Card key={item?.title +index} title={item.title} imagePath={item.imagePath}/>)}
        </Carousel>
    </>
  )
}

export default NewArrivals