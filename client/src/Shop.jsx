
import './Shop.css';
import HeroSection from './components/HeroSection/HeroSection';
import Category from './components/Sections/Category/Category';
import NewArrivals from './components/Sections/NewArrivals';
import content from './data/content.json';
import Footer from './components/Footer/Footer';

function Shop() {
  return (
    <>
      <HeroSection />
      <NewArrivals />
      {content?.pages.shop.sections && content?.pages.shop.sections.map((item, index)=> <Category key={item?.title+index} {...item} />)}
      <Footer content={content?.footer}/>
    </>
  );
}

export default Shop;
