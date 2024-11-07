
import './Shop.css';
import HeroSection from './components/HeroSection/HeroSection';
import Navigation from './components/Navigation/Navigation';
import NewArrivals from './components/Sections/NewArrivals';

function Shop() {
  return (
    <>
      <Navigation />
      <HeroSection />
      <NewArrivals />
    </>
  );
}

export default Shop;
