import SideBar from '../components/SideBar'
function Menu() {
    
    const menus = [
      {menu: 'Home', link: '/home'},
      {menu: 'Apply', link: '/home/apply'},
      {menu: 'About Us', link: '/home/about'},
      {menu: 'Logout', link: '/'}
    ]

    return <>
    <SideBar data={menus}/>
    </>
}

export default Menu;