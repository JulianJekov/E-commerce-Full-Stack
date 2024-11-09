
import './Shop.css';
import HeroSection from './components/HeroSection/HeroSection';
import Navigation from './components/Navigation/Navigation';
import Category from './components/Sections/Category/Category';
import NewArrivals from './components/Sections/NewArrivals';
import content from './data/content.json';
import Footer from './components/Footer/Footer';

function Shop() {
  return (
    <>
      <Navigation />
      <HeroSection />
      <NewArrivals />
      {content?.categories && content?.categories.map((item, index)=> <Category key={item?.title+index} {...item} />)}
      <Footer content={content?.footer[0]}/>
    </>
  );
}

export default Shop;
